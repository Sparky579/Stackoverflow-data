import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const SccaterChart = ({ data,xname,yname}) => {
  
  const chartRef = useRef(null);

  useEffect(() => {
    
    const chartInstance = echarts.init(chartRef.current);  
    const mappedData = data.map((value, index) => ({
        name: `${index + 1}`,
        value: value,
      }));

      

      const options =  {
          xAxis: {
            type: 'category',
            name:xname

          },
          yAxis: {
            type: 'value',
            name:yname
          },
          tooltip: {
            trigger: 'axis',
            formatter: xname+': {b} ' + yname+': {c}',
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

export default SccaterChart;
