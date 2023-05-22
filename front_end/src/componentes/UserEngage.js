import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const UserEngage = ({ data ,xname,yname}) => {
  const chartRef = useRef(null);

  useEffect(() => {
    const chartInstance = echarts.init(chartRef.current);
    echarts.registerTheme('customTheme', {
      color: ['#1890ff', '#40a9ff', '#69c0ff', '#91d5ff', '#bae7ff']
    });
    const xAxisData = Object.entries(data).map(([x, y]) => x).slice(0,3);
    const seriesData = Object.entries(data).map(([x, y]) => y).slice(0,3);
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
        left: 40,
        right: 40,
        bottom: 40
      },
      xAxis: {
        type: 'category',
        name: xname,
        axisLabel: { //设置x轴的字
            show:true,
            interval:0,//使x轴横坐标全部显示
            textStyle: {//x轴字体样式
              margin: 15,
            }
          },

        data: xAxisData
      },
      yAxis: {
        type: 'value',
        name: yname,
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
        formatter: xname
        +': {b} '+yname+' : {c}',
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

  return <div ref={chartRef} style={{ width: '100%', height: '400px' }}></div>;
};

export default UserEngage;
