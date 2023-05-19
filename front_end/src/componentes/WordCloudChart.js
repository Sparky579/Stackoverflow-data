import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const WordCloudChart = ({data}) => {
  const chartRef = useRef(null);
  // 转换数据为ECharts所需的格式
  const sortedData = data.sort((a, b) => b.value - a.value);

  useEffect(() => {
    
    const chartInstance = echarts.init(chartRef.current);
    const options = {
        title: {
          text: ''
        },
 
        series: [
          {
            type: 'pie',
            data: sortedData,
          },
        ]
      };      
    chartInstance.setOption(options);
  }, [data]);

  return (
    
    <div ref={chartRef} style={{ width: '100%', height: '400px' }}></div>
  );
};

export default WordCloudChart;
