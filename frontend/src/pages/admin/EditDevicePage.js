import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { connect } from 'react-redux';

import { unselectDevice } from 'store/actions';
import { getDevice, updateDevice } from 'services/api/devices';
import history from 'utils/history';

import DeviceForm from 'components/forms/DeviceForm';
import { addSensor, updateSensor } from 'services/api/sensor';
import { getClients } from 'services/api/users';

const ERR_MESSAGE = 'Unable to fetch the device';

const EditDevicePage = props => {
  const { selectedDevice, unselectDevice } = props;
  const { idDevice } = useParams();

  const [clients, setClients] = useState([]);
  const [device, setDevice] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const handleGetAllClients = async () => {
      const response = await getClients().catch(err => console.log(err));
      if (response) {
        setClients(response.data);
      }
    };
    const handleGetDevice = async () => {
      const response = await getDevice(idDevice).catch(err =>
        setError(ERR_MESSAGE)
      );
      if (response) {
        setDevice(response.data);
      }
    };
    handleGetAllClients();
    if (selectedDevice.id) {
      setDevice(selectedDevice);
    } else {
      handleGetDevice();
    }

    return () => {
      unselectDevice();
    };
  }, [idDevice, selectedDevice, unselectDevice]);

  const handleOnSubmit = async (deviceBody, sensorBody) => {
    const responseDevice = await updateDevice(deviceBody).catch(err =>
      setError(err.response.date.message)
    );
    let responseSensor;
    if (sensorBody.id) {
      responseSensor = await updateSensor(sensorBody).catch(err =>
        setError(err.response.date.message)
      );
    } else {
      responseSensor = await addSensor(deviceBody.id, sensorBody).catch(err =>
        setError(err.response.date.message)
      );
    }

    if (responseDevice && responseSensor) {
      history.goBack();
    }
  };

  const handleCloseAlertError = () => {
    setError(null);
  };

  if (!device) {
    return null;
  }

  return (
    <DeviceForm
      isEditMode={true}
      titleFormDevice="Update device"
      titleFormSensor={
        device.sensor ? 'Update sensor' : 'Attach sensor to device'
      }
      clients={clients}
      deviceClient={device.client}
      defaultDevice={device}
      defaultSensor={device.sensor}
      onSubmit={handleOnSubmit}
      buttonName="Update"
      error={error}
      onCloseAlert={handleCloseAlertError}
    />
  );
};

const mapStateToProps = state => {
  return { selectedDevice: state.selectedDevice };
};

export default connect(mapStateToProps, { unselectDevice })(EditDevicePage);
