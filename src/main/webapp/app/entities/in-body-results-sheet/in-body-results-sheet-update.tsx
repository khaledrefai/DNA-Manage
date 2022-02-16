import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './in-body-results-sheet.reducer';
import { IInBodyResultsSheet } from 'app/shared/model/in-body-results-sheet.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InBodyResultsSheetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const inBodyResultsSheetEntity = useAppSelector(state => state.inBodyResultsSheet.entity);
  const loading = useAppSelector(state => state.inBodyResultsSheet.loading);
  const updating = useAppSelector(state => state.inBodyResultsSheet.updating);
  const updateSuccess = useAppSelector(state => state.inBodyResultsSheet.updateSuccess);
  const handleClose = () => {
    props.history.push('/in-body-results-sheet' + props.location.search);
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
    values.datetimes = convertDateTimeToServer(values.datetimes);
    values.orderDate = convertDateTimeToServer(values.orderDate);

    const entity = {
      ...inBodyResultsSheetEntity,
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
          datetimes: displayDefaultDateTime(),
          orderDate: displayDefaultDateTime(),
        }
      : {
          ...inBodyResultsSheetEntity,
          datetimes: convertDateTimeFromServer(inBodyResultsSheetEntity.datetimes),
          orderDate: convertDateTimeFromServer(inBodyResultsSheetEntity.orderDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dnaManagementApplicationApp.inBodyResultsSheet.home.createOrEditLabel" data-cy="InBodyResultsSheetCreateUpdateHeading">
            <Translate contentKey="dnaManagementApplicationApp.inBodyResultsSheet.home.createOrEditLabel">
              Create or edit a InBodyResultsSheet
            </Translate>
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
                  id="in-body-results-sheet-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inBodyResultsSheet.userId')}
                id="in-body-results-sheet-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inBodyResultsSheet.datetimes')}
                id="in-body-results-sheet-datetimes"
                name="datetimes"
                data-cy="datetimes"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inBodyResultsSheet.orderDate')}
                id="in-body-results-sheet-orderDate"
                name="orderDate"
                data-cy="orderDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('dnaManagementApplicationApp.inBodyResultsSheet.inbodyImage')}
                id="in-body-results-sheet-inbodyImage"
                name="inbodyImage"
                data-cy="inbodyImage"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/in-body-results-sheet" replace color="info">
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

export default InBodyResultsSheetUpdate;
