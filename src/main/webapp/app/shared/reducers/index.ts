import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
import sessions from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import inhouseSample from 'app/entities/inhouse-sample/inhouse-sample.reducer';
// prettier-ignore
import dnaProfileType from 'app/entities/dna-profile-type/dna-profile-type.reducer';
// prettier-ignore
import projectType from 'app/entities/project-type/project-type.reducer';
// prettier-ignore
import sampleType from 'app/entities/sample-type/sample-type.reducer';
// prettier-ignore
import workPlace from 'app/entities/work-place/work-place.reducer';
// prettier-ignore
import sampleStatus from 'app/entities/sample-status/sample-status.reducer';
// prettier-ignore
import caseSample from 'app/entities/case-sample/case-sample.reducer';
// prettier-ignore
import caseSampleType from 'app/entities/case-sample-type/case-sample-type.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  inhouseSample,
  dnaProfileType,
  projectType,
  sampleType,
  workPlace,
  sampleStatus,
  caseSample,
  caseSampleType,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
