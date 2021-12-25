import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { connect } from 'react-redux';

import { getDevice } from 'services/api/devices';

import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Typography,
} from '@mui/material';

import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import DeviceSummary from 'components/summary/DeviceSummary';
import SensorSummary from 'components/summary/SensorSummary';
import RecordsSummary from 'components/summary/RecordsSummary';

const ClientDeviceInfoPage = props => {
  const { selectedDevice } = props;
  const { id } = useParams();

  const [device, setDevice] = useState(selectedDevice);

  useEffect(() => {
    const handleGetDevice = async () => {
      const response = await getDevice(id).catch(err =>
        console.log(err.response)
      );
      if (response) {
        setDevice(response.data);
      }
    };
    if (!selectedDevice.id) {
      handleGetDevice();
    } else {
      setDevice(selectedDevice);
    }
  }, [id, selectedDevice]);

  if (!device.id) {
    return null;
  }

  return (
    <React.Fragment>
      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="h5"> Device Info </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <DeviceSummary device={device} />
        </AccordionDetails>
      </Accordion>
      {device.sensor ? (
        <React.Fragment>
          <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography variant="h5"> Sensor Info </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <SensorSummary sensor={device.sensor} />
            </AccordionDetails>
          </Accordion>
          <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography variant="h5"> Records History </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <RecordsSummary records={device.sensor.records} />
            </AccordionDetails>
          </Accordion>
        </React.Fragment>
      ) : null}
    </React.Fragment>
  );
};

const mapStateToProps = state => {
  return {
    selectedDevice: state.selectedDevice,
  };
};

export default connect(mapStateToProps)(ClientDeviceInfoPage);
