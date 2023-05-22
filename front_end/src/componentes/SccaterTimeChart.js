import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const SccaterTimeChart = ({ data}) => {
  const chartRef = useRef(null);
  console.log("j",data);
  useEffect(() => {
    
    const chartInstance = echarts.init(chartRef.current);  
    const mappedData = data.map((value, index) => ({
        name: `${index + 1}`,
        value: value/3600,
      }));

      

      const options =  {
          xAxis: {
            type: 'category',

          },
          yAxis: {
            type: 'value',
          },
          tooltip: {
            trigger: 'axis',
            formatter: '问题id: {b} 时间: {c}',
            axisPointer: {
              type: 'shadow'
            }
          },
          series: [{
            type: 'scatter',
            data: mappedData,
          }],
        };
      
      
    chartInstance.setOption(options);
  }, [data]);

  return (
    
    <div ref={chartRef} style={{ width: '100%', height: '400px' }}></div>
  );
};

export default SccaterTimeChart;
