import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './project-type.reducer';
import { IProjectType } from 'app/shared/model/project-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProjectType = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const projectTypeList = useAppSelector(state => state.projectType.entities);
  const loading = useAppSelector(state => state.projectType.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="project-type-heading" data-cy="ProjectTypeHeading">
        <Translate contentKey="dnaManagementApplicationApp.projectType.home.title">Project Types</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dnaManagementApplicationApp.projectType.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dnaManagementApplicationApp.projectType.home.createLabel">Create new Project Type</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectTypeList && projectTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.projectType.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.projectType.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dnaManagementApplicationApp.projectType.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectTypeList.map((projectType, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${projectType.id}`} color="link" size="sm">
                      {projectType.id}
                    </Button>
                  </td>
                  <td>{projectType.name}</td>
                  <td>{projectType.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${projectType.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projectType.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projectType.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="dnaManagementApplicationApp.projectType.home.notFound">No Project Types found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ProjectType;
