import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';
import QuestionAnswerChart from './QuestionAnswerChart';

const QuestionAnswerCount = ({ data ,xname ,yname}) => {
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
        const normalizedValue = Math.floor(value);
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
        data: frequencyData.map((item) => item.value),
        name:xname
      },
      yAxis: {
        type: 'value',
        name:yname
      },
      tooltip: {
        trigger: 'axis',
        formatter:xname+': {b}' +yname+': {c}',
        axisPointer: {
          type: 'shadow'
        }
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

export default QuestionAnswerCount;
