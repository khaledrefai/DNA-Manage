import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './member-data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberDataDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const memberDataEntity = useAppSelector(state => state.memberData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="memberDataDetailsHeading">
          <Translate contentKey="dnaManagementApplicationApp.memberData.detail.title">MemberData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.id}</dd>
          <dt>
            <span id="testDate">
              <Translate contentKey="dnaManagementApplicationApp.memberData.testDate">Test Date</Translate>
            </span>
          </dt>
          <dd>
            {memberDataEntity.testDate ? <TextFormat value={memberDataEntity.testDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="userId">
              <Translate contentKey="dnaManagementApplicationApp.memberData.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.userId}</dd>
          <dt>
            <span id="userName">
              <Translate contentKey="dnaManagementApplicationApp.memberData.userName">User Name</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.userName}</dd>
          <dt>
            <span id="userGender">
              <Translate contentKey="dnaManagementApplicationApp.memberData.userGender">User Gender</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.userGender}</dd>
          <dt>
            <span id="userBirthday">
              <Translate contentKey="dnaManagementApplicationApp.memberData.userBirthday">User Birthday</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.userBirthday}</dd>
          <dt>
            <span id="userAge">
              <Translate contentKey="dnaManagementApplicationApp.memberData.userAge">User Age</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.userAge}</dd>
          <dt>
            <span id="userHeight">
              <Translate contentKey="dnaManagementApplicationApp.memberData.userHeight">User Height</Translate>
            </span>
          </dt>
          <dd>{memberDataEntity.userHeight}</dd>
          <dt>
            <span id="orderDate">
              <Translate contentKey="dnaManagementApplicationApp.memberData.orderDate">Order Date</Translate>
            </span>
          </dt>
          <dd>
            {memberDataEntity.orderDate ? <TextFormat value={memberDataEntity.orderDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/member-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/member-data/${memberDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MemberDataDetail;
