import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import { Button } from '@mui/material';

import { getDevices } from 'services/api/devices';
import Title from '../Title';
import DevicesTable from '../tables/DevicesTable';
import history from 'utils/history';

const NO_OF_DEVICES = 5;

const DevicesList = props => {
  const { title } = props;
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    const handleGetDevices = async () => {
      const response = await getDevices(1, NO_OF_DEVICES).catch(err =>
        console.log(err.response)
      );
      if (response) {
        setDevices(response.data);
      }
    };
    handleGetDevices();
  }, []);

  const handleSelectDevice = device => {
    history.push(`/admin/devices/update/${device.client.id}/${device.id}`);
  };

  return (
    <React.Fragment>
      <Title>{title}</Title>
      <DevicesTable devices={devices} onSelectDevice={handleSelectDevice} />
      <Link
        className="link"
        to="/admin/devices"
        style={{ marginTop: '1rem', width: '100%' }}
      >
        <Button color="primary" variant="outlined" size="small" fullWidth>
          View All
        </Button>
      </Link>
    </React.Fragment>
  );
};

export default DevicesList;
