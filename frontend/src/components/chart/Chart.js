import React from 'react';
import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from 'recharts';

const Chart = props => {
  const { data } = props;

  const renderCharts = () => {
    const keys = Object.keys(data[0]);
    return keys
      .filter(key => key !== 'hour')
      .map(k => (
        <Line
          key={k}
          type="monotone"
          dataKey={k}
          stroke={`#${Math.floor(Math.random() * 16777215).toString(16)}`}
        />
      ));
  };

  if (data.length < 1) {
    return null;
  }

  return (
    <ResponsiveContainer width="100%" height={400}>
      <LineChart data={data}>
        {renderCharts()}
        <CartesianGrid stroke="#ccc" />
        <XAxis dataKey="hour" />
        <YAxis />
        <Tooltip />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default Chart;
