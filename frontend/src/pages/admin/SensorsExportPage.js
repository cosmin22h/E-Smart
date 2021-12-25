import React, { useState, useEffect } from 'react';
import { CSVLink } from 'react-csv';

import { Paper, Button } from '@mui/material';

import { getDetails } from 'services/api/sensor';
import SensorsDetailsTable from 'components/tables/SensorsDetailsTable';

const SensorsExportPage = () => {
  const [sensors, setSensors] = useState([]);

  useEffect(() => {
    const getSensorsDetails = async () => {
      const response = await getDetails().catch(error => console.log(error));
      if (response) {
        setSensors(response.data);
      }
    };
    getSensorsDetails();
  }, []);

  const mapSensorsIds = () => {
    const sensorsIds = sensors.map(sensor =>
      Object.fromEntries(
        Object.entries(sensor).filter(([key, value]) => key === 'id')
      )
    );

    return sensorsIds;
  };

  return (
    <React.Fragment>
      <CSVLink
        data={mapSensorsIds()}
        filename={'sensors.csv'}
        style={{ textDecoration: 'none' }}
      >
        <Button variant="contained" fullWidth sx={{ mb: 2 }}>
          Export All Sensors IDs as CSV
        </Button>
      </CSVLink>
      <Paper sx={{ p: 2 }}>
        <SensorsDetailsTable sensors={sensors} />
      </Paper>
    </React.Fragment>
  );
};

export default SensorsExportPage;
