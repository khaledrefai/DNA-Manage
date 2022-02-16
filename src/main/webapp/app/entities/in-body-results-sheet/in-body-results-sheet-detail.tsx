import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './in-body-results-sheet.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InBodyResultsSheetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const inBodyResultsSheetEntity = useAppSelector(state => state.inBodyResultsSheet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inBodyResultsSheetDetailsHeading">
          <Translate contentKey="dnaManagementApplicationApp.inBodyResultsSheet.detail.title">InBodyResultsSheet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inBodyResultsSheetEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="dnaManagementApplicationApp.inBodyResultsSheet.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{inBodyResultsSheetEntity.userId}</dd>
          <dt>
            <span id="datetimes">
              <Translate contentKey="dnaManagementApplicationApp.inBodyResultsSheet.datetimes">Datetimes</Translate>
            </span>
          </dt>
          <dd>
            {inBodyResultsSheetEntity.datetimes ? (
              <TextFormat value={inBodyResultsSheetEntity.datetimes} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="orderDate">
              <Translate contentKey="dnaManagementApplicationApp.inBodyResultsSheet.orderDate">Order Date</Translate>
            </span>
          </dt>
          <dd>
            {inBodyResultsSheetEntity.orderDate ? (
              <TextFormat value={inBodyResultsSheetEntity.orderDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="inbodyImage">
              <Translate contentKey="dnaManagementApplicationApp.inBodyResultsSheet.inbodyImage">Inbody Image</Translate>
            </span>
          </dt>
          <dd>{inBodyResultsSheetEntity.inbodyImage}</dd>
        </dl>
        <Button tag={Link} to="/in-body-results-sheet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/in-body-results-sheet/${inBodyResultsSheetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InBodyResultsSheetDetail;
