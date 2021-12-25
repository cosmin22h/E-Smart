import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';

import {
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
  Button,
  Typography,
} from '@mui/material';

import { getDevicesForClient } from 'services/api/devices';
import { getBestProgramToStartDevice } from 'services/api/rpc';

import Chart from 'components/chart/Chart';
import Title from 'components/Title';

const ProgramTab = () => {
  const auth = useSelector(state => state.auth);

  const [devices, setDevices] = useState([]);
  const [selectedDevice, setSelectedDevice] = useState(null);
  const [program, setProgram] = useState(3);
  const [bestEstimate, setBestEstimate] = useState(null);

  useEffect(() => {
    const getDevices = async () => {
      const response = await getDevicesForClient(auth.id);
      if (response) {
        const devices = response.data;
        setDevices(devices);
        if (devices.length > 0) {
          setSelectedDevice(devices[0]);
        }
      }
    };
    getDevices();
  }, [auth]);

  const handleOnClickShowGraph = async () => {
    if (program < 1 || program > 23) return;
    const response = await getBestProgramToStartDevice(
      auth.id,
      selectedDevice.id,
      parseInt(program) + 1
    );
    if (response.data) {
      const data = response.data.result;
      const dataArray = data.chart.map((d, i) => {
        return { hour: i, value: d };
      });
      setBestEstimate({
        startHour: data.startHour,
        chart: dataArray,
        currentConsumption: data.currentConsumption,
        estimatedConsumption: data.estimatedConsumption,
      });
    }
  };

  if (!selectedDevice) {
    return null;
  }

  return (
    <Box>
      <FormControl fullWidth required style={{ marginTop: '1rem' }}>
        <InputLabel>Device</InputLabel>
        <Select
          id="selectedDevice"
          name="selectedDevice"
          label="Device"
          value={selectedDevice}
          onChange={e => {
            setSelectedDevice(e.target.value);
          }}
        >
          {devices.map(device => (
            <MenuItem key={device.id} value={device}>
              {device.description}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField
        value={program}
        onChange={e => setProgram(e.target.value)}
        name="day"
        label="Select the program (in hours)"
        type="number"
        InputProps={{ inputProps: { min: 1 } }}
        fullWidth
        sx={{ mt: 2 }}
      />
      <Button
        variant="contained"
        sx={{ mt: 2, width: '100%' }}
        onClick={handleOnClickShowGraph}
      >
        SHOW BEST PROGRAM
      </Button>
      {bestEstimate && bestEstimate.chart.length > 0 ? (
        <React.Fragment>
          <div style={{ marginTop: '1rem' }}>
            <Title variant="h5">Best Estimated Program</Title>
          </div>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              marginBottom: '1rem',
            }}
          >
            <Typography variant="p">
              Start Program: {bestEstimate.startHour}
            </Typography>
            <Typography variant="p">
              End Program:{' '}
              {parseInt(bestEstimate.startHour) + parseInt(program)}
            </Typography>
            <Typography variant="p">
              Current Consumption: {bestEstimate.currentConsumption.toFixed(2)}{' '}
              kW/h
            </Typography>
            <Typography variant="p">
              Estimated Consumption{' '}
              {bestEstimate.estimatedConsumption.toFixed(2)} kW/h
            </Typography>
          </div>
          <Chart data={bestEstimate.chart} />{' '}
        </React.Fragment>
      ) : null}
    </Box>
  );
};

export default ProgramTab;
