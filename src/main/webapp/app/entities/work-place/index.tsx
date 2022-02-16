import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WorkPlace from './work-place';
import WorkPlaceDetail from './work-place-detail';
import WorkPlaceUpdate from './work-place-update';
import WorkPlaceDeleteDialog from './work-place-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorkPlaceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorkPlaceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorkPlaceDetail} />
      <ErrorBoundaryRoute path={match.url} component={WorkPlace} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorkPlaceDeleteDialog} />
  </>
);

export default Routes;
