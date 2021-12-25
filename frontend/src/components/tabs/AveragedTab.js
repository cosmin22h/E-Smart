import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';

import { getBaseline } from 'services/api/rpc';
import Chart from 'components/chart/Chart';

const AveragedTab = props => {
  const auth = useSelector(state => state.auth);
  const [dateBaseline, setDateBaseline] = useState([]);

  useEffect(() => {
    const getBaselineForClient = async () => {
      const response = await getBaseline(auth.id);
      if (response) {
        setDateBaseline(
          response.data.result.map((d, i) => {
            return { hour: i, val: d };
          })
        );
      }
    };
    getBaselineForClient();
  }, [auth]);

  if (dateBaseline.length < 1) {
    return null;
  }

  return <Chart data={dateBaseline} />;
};

export default AveragedTab;
