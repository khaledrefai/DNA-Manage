import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SampleType from './sample-type';
import SampleTypeDetail from './sample-type-detail';
import SampleTypeUpdate from './sample-type-update';
import SampleTypeDeleteDialog from './sample-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SampleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SampleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SampleTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={SampleType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SampleTypeDeleteDialog} />
  </>
);

export default Routes;
