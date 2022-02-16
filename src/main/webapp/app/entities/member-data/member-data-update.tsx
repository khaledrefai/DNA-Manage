import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './member-data.reducer';
import { IMemberData } from 'app/shared/model/member-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberDataUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const memberDataEntity = useAppSelector(state => state.memberData.entity);
  const loading = useAppSelector(state => state.memberData.loading);
  const updating = useAppSelector(state => state.memberData.updating);
  const updateSuccess = useAppSelector(state => state.memberData.updateSuccess);
  const handleClose = () => {
    props.history.push('/member-data');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.testDate = convertDateTimeToServer(values.testDate);
    values.orderDate = convertDateTimeToServer(values.orderDate);

    const entity = {
      ...memberDataEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          testDate: displayDefaultDateTime(),
          orderDate: displayDefaultDateTime(),
        }
      : {
          ...memberDataEntity,
          testDate: convertDateTimeFromServer(memberDataEntity.testDate),
          orderDate: convertDateTimeFromServer(memberDataEntity.orderDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dnaManagementApplicationApp.memberData.home.createOrEditLabel" data-cy="MemberDataCreateUpdateHeading">
            <Translate contentKey="dnaManagementApplicationApp.memberData.home.createOrEditLabel">Create or edit a MemberData</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="member-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.testDate')}
                id="member-data-testDate"
                name="testDate"
                data-cy="testDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.userId')}
                id="member-data-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.userName')}
                id="member-data-userName"
                name="userName"
                data-cy="userName"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.userGender')}
                id="member-data-userGender"
                name="userGender"
                data-cy="userGender"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.userBirthday')}
                id="member-data-userBirthday"
                name="userBirthday"
                data-cy="userBirthday"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.userAge')}
                id="member-data-userAge"
                name="userAge"
                data-cy="userAge"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.userHeight')}
                id="member-data-userHeight"
                name="userHeight"
                data-cy="userHeight"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.memberData.orderDate')}
                id="member-data-orderDate"
                name="orderDate"
                data-cy="orderDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/member-data" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MemberDataUpdate;
