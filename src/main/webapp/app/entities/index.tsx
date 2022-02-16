import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InhouseSample from './inhouse-sample';
import DnaProfileType from './dna-profile-type';
import ProjectType from './project-type';
import SampleType from './sample-type';
import WorkPlace from './work-place';
import SampleStatus from './sample-status';
import CaseSample from './case-sample';
import CaseSampleType from './case-sample-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}inhouse-sample`} component={InhouseSample} />
      <ErrorBoundaryRoute path={`${match.url}dna-profile-type`} component={DnaProfileType} />
      <ErrorBoundaryRoute path={`${match.url}project-type`} component={ProjectType} />
      <ErrorBoundaryRoute path={`${match.url}sample-type`} component={SampleType} />
      <ErrorBoundaryRoute path={`${match.url}work-place`} component={WorkPlace} />
      <ErrorBoundaryRoute path={`${match.url}sample-status`} component={SampleStatus} />
      <ErrorBoundaryRoute path={`${match.url}case-sample`} component={CaseSample} />
      <ErrorBoundaryRoute path={`${match.url}case-sample-type`} component={CaseSampleType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
