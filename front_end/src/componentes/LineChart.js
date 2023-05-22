import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const LineChart = ({ data ,xname,yname}) => {
  const chartRef = useRef(null);

  useEffect(() => {
    const chartInstance = echarts.init(chartRef.current);

    const options = {
      title: {
      },
      xAxis: {
        type: 'category',
        data: Object.keys(data),
        name:xname
      },
      yAxis: {
        type: 'value',
        name:yname
      },
      tooltip: {
        trigger: 'axis',
        formatter: xname+': {b} '+yname+': {c}',
        axisPointer: {
          type: 'shadow'
        }
      },
      series: [{
        data: Object.values(data),
        type: "line",
        color: "#1890ff", // 线条颜色
        areaStyle: {
          color: "rgba(24,144,255,0.08)", // 区域背景色
        },

      }

    ]
    };

    chartInstance.setOption(options);

    return () => {
      chartInstance.dispose();
    };
  }, [data]);

  return (
    <div ref={chartRef} style={{ width: '100%', height: '400px' }}></div>
  );
};

export default LineChart;