import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './member-data.reducer';
import { IMemberData } from 'app/shared/model/member-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberData = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const memberDataList = useAppSelector(state => state.memberData.entities);
  const loading = useAppSelector(state => state.memberData.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="member-data-heading" data-cy="MemberDataHeading">
        <Translate contentKey="dnaManagementApplicationApp.memberData.home.title">Member Data</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dnaManagementApplicationApp.memberData.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dnaManagementApplicationApp.memberData.home.createLabel">Create new Member Data</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {memberDataList && memberDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.testDate">Test Date</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.userId">User Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.userName">User Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.userGender">User Gender</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.userBirthday">User Birthday</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.userAge">User Age</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.userHeight">User Height</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.memberData.orderDate">Order Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {memberDataList.map((memberData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${memberData.id}`} color="link" size="sm">
                      {memberData.id}
                    </Button>
                  </td>
                  <td>{memberData.testDate ? <TextFormat type="date" value={memberData.testDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{memberData.userId}</td>
                  <td>{memberData.userName}</td>
                  <td>{memberData.userGender}</td>
                  <td>{memberData.userBirthday}</td>
                  <td>{memberData.userAge}</td>
                  <td>{memberData.userHeight}</td>
                  <td>{memberData.orderDate ? <TextFormat type="date" value={memberData.orderDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${memberData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${memberData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${memberData.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="dnaManagementApplicationApp.memberData.home.notFound">No Member Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MemberData;
