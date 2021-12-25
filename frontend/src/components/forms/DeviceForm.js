import React, { useState, useEffect } from 'react';

import {
  Box,
  Paper,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Menu,
  IconButton,
} from '@mui/material';
import MoreIcon from '@mui/icons-material/More';

import { deleteDevice } from 'services/api/devices';
import { deleteSensor } from 'services/api/sensor';
import useInput from 'utils/hooks/useInput';
import Title from 'components/Title';
import ClosingAlert from 'components/alerts/ClosingAlert';
import history from 'utils/history';
import ConfirmationModal from 'components/modals/ConfirmationModal';

const DeviceForm = props => {
  const {
    isEditMode,
    titleFormDevice,
    titleFormSensor,
    deviceClient,
    clients,
    defaultDevice,
    defaultSensor,
    onSubmit,
    buttonName,
    error,
    onCloseAlert,
  } = props;

  let initSensor;
  if (!defaultSensor) {
    initSensor = {
      description: '',
      maxValue: 0,
    };
  } else {
    initSensor = defaultSensor;
  }

  const [viewSensor, setViewSensor] = useState(
    (isEditMode && defaultSensor) || !isEditMode
  );
  const [isDeleteModalOpenForSensor, setIsDeleteModalOpenForSensor] =
    useState(false);
  const [isDeleteModalOpenForDevice, setIsDeleteModalOpenForDevice] =
    useState(false);
  const [client, setClient] = useState(null);
  const [device, onDeviceChange] = useInput(defaultDevice);
  const [sensor, onSensorChange] = useInput(initSensor);
  const [anchorEl, setAnchorEl] = useState(null);

  useEffect(() => {
    let selectedClient = clients[0];
    if (deviceClient) {
      selectedClient = clients.find(client => client.id === deviceClient.id);
    }
    setClient(selectedClient);
  }, [deviceClient, clients]);

  const handleOpenMenu = e => {
    setAnchorEl(e.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleViewClient = idClient => {
    history.push(`/admin/clients/update/${idClient}`);
  };

  const handleAttachSensor = () => {
    setViewSensor(true);
  };

  const handleOpenModalSensor = () => {
    setIsDeleteModalOpenForSensor(true);
  };

  const handleOpenModalDevice = () => {
    setIsDeleteModalOpenForDevice(true);
  };

  const handleDeleteDevice = async () => {
    const response = await deleteDevice(device.id).catch(err =>
      console.log(err.response)
    );
    if (response) {
      history.goBack();
    }
  };

  const handleConfirmDetachSensor = async () => {
    const response = await deleteSensor(sensor.id).catch(err =>
      console.log(err.response)
    );
    if (response) {
      history.goBack();
    }
  };

  const handleOnSubmit = event => {
    event.preventDefault();
    if (isEditMode) {
      onSubmit({ ...device, client }, sensor);
    } else {
      onSubmit({ ...device, sensor }, client);
    }
  };

  const renderHeaderButtons = () => {
    return (
      <div>
        <IconButton color="primary" onClick={handleOpenMenu}>
          <MoreIcon />
        </IconButton>
        <Menu anchorEl={anchorEl} open={!!anchorEl} onClose={handleCloseMenu}>
          <MenuItem onClick={() => handleViewClient(device.client.id)}>
            View client
          </MenuItem>
          <MenuItem onClick={handleOpenModalDevice}>Delete device</MenuItem>
          {defaultSensor ? (
            <MenuItem onClick={handleOpenModalSensor}>Detach Sensor</MenuItem>
          ) : (
            <MenuItem onClick={handleAttachSensor}>Attach Sensor</MenuItem>
          )}
        </Menu>
      </div>
    );
  };

  const renderSensorFields = () => (
    <Box sx={{ mt: 2 }}>
      <Title>{titleFormSensor}</Title>
      <TextField
        id="description"
        name="description"
        type="text"
        label="Sensor description"
        value={sensor.description}
        required
        fullWidth
        multiline
        rows={4}
        onChange={onSensorChange}
        sx={{ mt: 1 }}
      />
      <TextField
        id="maxValue"
        name="maxValue"
        type="number"
        step="10"
        label="Sensor max value"
        value={sensor.maxValue}
        required
        fullWidth
        onChange={onSensorChange}
        sx={{ mt: 2 }}
        InputProps={{ inputProps: { min: 0, step: 0.001 } }}
      />
    </Box>
  );

  if (!client) {
    return null;
  }

  return (
    <React.Fragment>
      <Paper sx={{ p: 2 }}>
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-between',
          }}
        >
          <div style={{ marginRight: '1rem' }}>
            <Title>{titleFormDevice}</Title>
          </div>
          {isEditMode ? renderHeaderButtons() : null}
        </div>
        {error ? (
          <ClosingAlert
            severity="error"
            message={error}
            onClose={onCloseAlert}
          />
        ) : null}
        <FormControl fullWidth required style={{ marginTop: '1rem' }}>
          <InputLabel>Client</InputLabel>
          <Select
            id="username"
            name="username"
            label="Client"
            value={client}
            onChange={e => {
              setClient(e.target.value);
            }}
          >
            {clients.map(clientItem => (
              <MenuItem key={clientItem.id} value={clientItem}>
                {clientItem.username}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <Box component="form" sx={{ mt: 2 }} onSubmit={handleOnSubmit}>
          <TextField
            id="description"
            name="description"
            type="text"
            label="Device description"
            value={device.description}
            required
            fullWidth
            multiline
            rows={4}
            onChange={onDeviceChange}
          />
          <TextField
            id="location"
            name="location"
            type="text"
            label="Device location"
            value={device.location}
            required
            fullWidth
            onChange={onDeviceChange}
            sx={{ mt: 2 }}
          />
          {viewSensor ? renderSensorFields() : null}
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
            {buttonName}
          </Button>
        </Box>
      </Paper>
      {isDeleteModalOpenForSensor ? (
        <ConfirmationModal
          title="Are you sure you want to detach this sensor from his device?"
          bodyText="The sensor will be permanently deleted!"
          btnTextConfirm="Delete"
          open={isDeleteModalOpenForSensor}
          onConfirm={handleConfirmDetachSensor}
          onClose={() => setIsDeleteModalOpenForSensor(false)}
        />
      ) : null}
      {isDeleteModalOpenForDevice ? (
        <ConfirmationModal
          title="Are you sure you want to delete this device?"
          btnTextConfirm="Delete"
          open={isDeleteModalOpenForDevice}
          onConfirm={handleDeleteDevice}
          onClose={() => setIsDeleteModalOpenForDevice(false)}
        />
      ) : null}
    </React.Fragment>
  );
};

DeviceForm.defaultProps = {
  isEditMode: false,
  deviceClient: null,
  defaultDevice: {
    idClient: '',
    description: '',
    location: '',
  },
  defaultSensor: {
    description: '',
    maxValue: 0,
  },
};

export default DeviceForm;
