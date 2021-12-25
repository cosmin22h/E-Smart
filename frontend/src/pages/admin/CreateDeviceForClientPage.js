import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';

import DeviceForm from 'components/forms/DeviceForm';
import { getClient } from 'services/api/users';
import { addDevice } from 'services/api/devices';
import history from 'utils/history';

const ERR_MESSAGE = 'Unable to fetch the user!';

const CreateDeviceForClientPage = props => {
  const { id } = useParams();
  const [client, setClient] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const handleGetClient = async () => {
      const response = await getClient(id).catch(err => setError(ERR_MESSAGE));
      if (response) {
        setClient(response.data);
      }
    };
    if (id) {
      handleGetClient();
    }
  }, [id]);

  const handleCloseError = () => {
    setError(null);
  };

  const handleOnSubmit = async newDevice => {
    const response = await addDevice({ ...newDevice, idClient: id }).catch(
      err => setError(err.response.data.message)
    );
    if (response) {
      history.goBack();
    }
  };

  return (
    <DeviceForm
      titleFormDevice="Add Device for client"
      titleFormSensor="Attach sensor to device"
      clients={[client]}
      onSubmit={handleOnSubmit}
      buttonName="Add"
      error={error}
      onCloseAlert={handleCloseError}
    />
  );
};

export default CreateDeviceForClientPage;
