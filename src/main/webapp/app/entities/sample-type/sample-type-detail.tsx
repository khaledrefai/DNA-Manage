import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sample-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SampleTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sampleTypeEntity = useAppSelector(state => state.sampleType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sampleTypeDetailsHeading">
          <Translate contentKey="dnaManagementApplicationApp.sampleType.detail.title">SampleType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sampleTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="dnaManagementApplicationApp.sampleType.name">Name</Translate>
            </span>
          </dt>
          <dd>{sampleTypeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="dnaManagementApplicationApp.sampleType.description">Description</Translate>
            </span>
          </dt>
          <dd>{sampleTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/sample-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sample-type/${sampleTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SampleTypeDetail;
