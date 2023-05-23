import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const Test = ({ data }) => {
  const chartRef = useRef(null);

  useEffect(() => {
    if (data) {
      renderChart();
    }
  }, [data]);

  const renderChart = () => {
    const chart = echarts.init(chartRef.current);

    const frequencyMap = {};
    data.forEach((value) => {
        const normalizedValue = Math.floor(value / 3600/24);
      frequencyMap[normalizedValue] = frequencyMap[normalizedValue] ? frequencyMap[normalizedValue] + 1 : 1;
    });

    console.log("fre",frequencyMap);

    const frequencyData = Object.keys(frequencyMap).map((key) => ({
      value: key,
      frequency: frequencyMap[key],
    }));

    const options = {
      xAxis: {
        type: 'category',
        name:'天数',
        data: frequencyData.map((item) => item.value),
      },
      yAxis: {
        type: 'value',
        name:'问题个数'
      },
      series: [
        {
          type: 'bar',
          data: frequencyData.map((item) => item.frequency),
        },
      ],
    };

    chart.setOption(options);
  };

  return <div ref={chartRef} style={{ width: '100%', height: '300px' }} />;
};

export default Test;
