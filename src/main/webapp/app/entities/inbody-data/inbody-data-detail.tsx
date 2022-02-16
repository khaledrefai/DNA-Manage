import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './inbody-data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InbodyDataDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const inbodyDataEntity = useAppSelector(state => state.inbodyData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inbodyDataDetailsHeading">
          <Translate contentKey="dnaManagementApplicationApp.inbodyData.detail.title">InbodyData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inbodyDataEntity.id}</dd>
          <dt>
            <span id="userID">
              <Translate contentKey="dnaManagementApplicationApp.inbodyData.userID">User ID</Translate>
            </span>
          </dt>
          <dd>{inbodyDataEntity.userID}</dd>
          <dt>
            <span id="columnName">
              <Translate contentKey="dnaManagementApplicationApp.inbodyData.columnName">Column Name</Translate>
            </span>
          </dt>
          <dd>{inbodyDataEntity.columnName}</dd>
          <dt>
            <span id="columnValue">
              <Translate contentKey="dnaManagementApplicationApp.inbodyData.columnValue">Column Value</Translate>
            </span>
          </dt>
          <dd>{inbodyDataEntity.columnValue}</dd>
        </dl>
        <Button tag={Link} to="/inbody-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inbody-data/${inbodyDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InbodyDataDetail;
