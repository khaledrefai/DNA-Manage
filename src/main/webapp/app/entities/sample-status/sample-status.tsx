import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './sample-status.reducer';
import { ISampleStatus } from 'app/shared/model/sample-status.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SampleStatus = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const sampleStatusList = useAppSelector(state => state.sampleStatus.entities);
  const loading = useAppSelector(state => state.sampleStatus.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="sample-status-heading" data-cy="SampleStatusHeading">
        <Translate contentKey="dnaManagementApplicationApp.sampleStatus.home.title">Sample Statuses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dnaManagementApplicationApp.sampleStatus.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dnaManagementApplicationApp.sampleStatus.home.createLabel">Create new Sample Status</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sampleStatusList && sampleStatusList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.sampleStatus.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.sampleStatus.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.sampleStatus.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sampleStatusList.map((sampleStatus, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${sampleStatus.id}`} color="link" size="sm">
                      {sampleStatus.id}
                    </Button>
                  </td>
                  <td>{sampleStatus.name}</td>
                  <td>{sampleStatus.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${sampleStatus.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sampleStatus.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${sampleStatus.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="dnaManagementApplicationApp.sampleStatus.home.notFound">No Sample Statuses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SampleStatus;
