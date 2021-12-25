import React, { useState, useEffect } from 'react';

import { getClients } from 'services/api/users';
import { addDevice } from 'services/api/devices';
import history from 'utils/history';
import DeviceForm from 'components/forms/DeviceForm';

const ERR_MESSAGE = 'Unable to fetch the users!';

const CreateDevicePage = props => {
  const [clients, setClients] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const handleGetClients = async () => {
      const response = await getClients().catch(err => setError(ERR_MESSAGE));
      if (response) {
        setClients(response.data);
      }
    };
    handleGetClients();
  }, []);

  const handleCloseError = () => {
    setError(null);
  };

  const handleOnSubmit = async (newDevice, client) => {
    const response = await addDevice({
      ...newDevice,
      idClient: client.id,
    }).catch(err => setError(err.response.data.message));
    if (response) {
      history.goBack();
    }
  };

  return (
    <DeviceForm
      titleFormDevice="Add Device"
      titleFormSensor="Attach sensor to device"
      clients={clients}
      onSubmit={handleOnSubmit}
      buttonName="Add"
      error={error}
      onCloseAlert={handleCloseError}
    />
  );
};

export default CreateDevicePage;
