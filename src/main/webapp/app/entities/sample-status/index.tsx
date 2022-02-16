import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SampleStatus from './sample-status';
import SampleStatusDetail from './sample-status-detail';
import SampleStatusUpdate from './sample-status-update';
import SampleStatusDeleteDialog from './sample-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SampleStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SampleStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SampleStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={SampleStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SampleStatusDeleteDialog} />
  </>
);

export default Routes;
