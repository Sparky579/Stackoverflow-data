import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const AnswerDistributionChart = ({ data }) => {
  const chartRef = useRef(null);

  useEffect(() => {
    const chartInstance = echarts.init(chartRef.current);
    echarts.registerTheme('customTheme', {
      color: ['#1890ff', '#40a9ff', '#69c0ff', '#91d5ff', '#bae7ff']
    });
    const xAxisData = Object.entries(data).map(([x, y]) => x);
    const seriesData = Object.entries(data).map(([x, y]) => y);
    chartInstance.setOption({
      title: {
        text: '',
        textStyle: {
          fontWeight: 'bold',
          fontSize: 16
        }
      },
      grid: {
        top: 40,
        left: 40,
        right: 40,
        bottom: 40
      },
      xAxis: {
        type: 'category',
        name: '回答数量',
        axisLabel: {
          textStyle: {
            color: '#333'
          }
        },
        data: xAxisData
      },
      yAxis: {
        type: 'value',
        name: '问题个数',
        axisLabel: {
          textStyle: {
            color: '#333'
          }
        },
        splitLine: {
          lineStyle: {
            type: 'dashed'
          }
        }
      },
      tooltip: {
        trigger: 'axis',
        formatter: 'Answer_count: {b} Question_Count: {c}',
        axisPointer: {
          type: 'shadow'
        }
      },
      series: [
        {
          type: 'bar',
          data: seriesData,
          itemStyle: {
            emphasis: {
              color: '#1890ff'
            }
          }
        }
      ]
    });
  }, [data]);

  return <div ref={chartRef} style={{ width: '100%', height: '400px' }}></div>;
};

export default AnswerDistributionChart;
