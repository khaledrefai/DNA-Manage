import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InBodyResultsSheet from './in-body-results-sheet';
import InBodyResultsSheetDetail from './in-body-results-sheet-detail';
import InBodyResultsSheetUpdate from './in-body-results-sheet-update';
import InBodyResultsSheetDeleteDialog from './in-body-results-sheet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InBodyResultsSheetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InBodyResultsSheetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InBodyResultsSheetDetail} />
      <ErrorBoundaryRoute path={match.url} component={InBodyResultsSheet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InBodyResultsSheetDeleteDialog} />
  </>
);

export default Routes;
