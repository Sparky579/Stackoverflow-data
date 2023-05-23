import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const CombineTagVote = ({ data }) => {
  const chartRef = useRef(null);
  

  useEffect(() => {
    const chartInstance = echarts.init(chartRef.current);
    echarts.registerTheme('customTheme', {
      color: ['#1890ff', '#40a9ff', '#69c0ff', '#91d5ff', '#bae7ff']
    });
    const xAxisData = Object.entries(data).map(([x, y]) => x).slice(0,10);
    const seriesData = Object.entries(data).map(([x, y]) => y).slice(0,10);
    chartInstance.setOption({
      title: {
        text: '',
        textStyle: {
          fontWeight: 'bold',
          fontSize: 10
        }
      },
      grid: {
        top: 40,
        left: 100,
        right: 100,
        bottom: 200
      },
      xAxis: {
        type: 'category',
        name: 'Tag组合',
             // x轴文字倾斜
             axisLabel:{
              interval:0,
              rotate:45,//倾斜度 -90 至 90 默认为0
              margin:2,
              textStyle:{
                fontWeight:"bolder",
                color:"#000000"
              }
            },

        data: xAxisData
      },
      yAxis: {
        type: 'value',
        name: 'Vote个数',
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
        formatter: 'Tag组合: {b} View次数: {c}',
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
                label: {
                  show: true,
                  fontSize: '30',
                  fontWeight: 'bold',
                },
              },
          },
          
        }
      ]
    });
  }, [data]);

  return <div ref={chartRef} style={{ width: '100%', height: '600px' }}></div>;
};

export default CombineTagVote;
