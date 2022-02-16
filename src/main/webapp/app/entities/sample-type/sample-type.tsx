import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './sample-type.reducer';
import { ISampleType } from 'app/shared/model/sample-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SampleType = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const sampleTypeList = useAppSelector(state => state.sampleType.entities);
  const loading = useAppSelector(state => state.sampleType.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="sample-type-heading" data-cy="SampleTypeHeading">
        <Translate contentKey="dnaManagementApplicationApp.sampleType.home.title">Sample Types</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dnaManagementApplicationApp.sampleType.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dnaManagementApplicationApp.sampleType.home.createLabel">Create new Sample Type</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sampleTypeList && sampleTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.sampleType.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.sampleType.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.sampleType.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sampleTypeList.map((sampleType, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${sampleType.id}`} color="link" size="sm">
                      {sampleType.id}
                    </Button>
                  </td>
                  <td>{sampleType.name}</td>
                  <td>{sampleType.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${sampleType.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sampleType.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sampleType.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="dnaManagementApplicationApp.sampleType.home.notFound">No Sample Types found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SampleType;
