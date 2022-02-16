import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectType from './project-type';
import ProjectTypeDetail from './project-type-detail';
import ProjectTypeUpdate from './project-type-update';
import ProjectTypeDeleteDialog from './project-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjectTypeDeleteDialog} />
  </>
);

export default Routes;
