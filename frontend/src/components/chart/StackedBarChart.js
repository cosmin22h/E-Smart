import React from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from 'recharts';

const StackedBarChart = props => {
  const { data } = props;

  const renderY = () => {
    return Object.keys(data[0])
      .filter(k => k !== 'hour')
      .map(k => (
        <Bar
          key={k}
          dataKey={k}
          stackId="a"
          fill={`#${Math.floor(Math.random() * 16777215).toString(16)}`}
        />
      ));
  };

  return (
    <ResponsiveContainer width="100%" height={500}>
      <BarChart
        data={data}
        margin={{
          top: 20,
          right: 30,
          left: 20,
          bottom: 5,
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="hour" />
        <YAxis />
        <Tooltip />
        {renderY()}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default StackedBarChart;
