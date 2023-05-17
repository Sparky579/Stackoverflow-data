import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';

const QuestionAnswerChart = ({ data,average,max}) => {
  const chartRef = useRef(null);

  useEffect(() => {
    
    const chartInstance = echarts.init(chartRef.current);
    const options = {
        title: {
          text: '问题回答数量散点图'
        },
        xAxis: {
        type: 'category',
          name: '问题 ID'
        },
        yAxis: {
          type: 'value',
          name: '回答数量'
        },
        series: [
          {
            type: 'scatter',
            symbolSize: 10,
            data: data.map(item => [item.question_id, item.answer_count])
          },
          {
            type: 'line',
            markLine: {
              label: {
                show: true,
                position: 'start',
                formatter: '平均值\n'+average,
              },
              lineStyle: {
                color: '#007bff'
              },
              data: [
                {
                  yAxis: average,
                  lineStyle: {
                    color: '#77D048',
                    width: 1,
                    opacity: 0.8
                  }
                }
              ]
            }
          },
          {
            type: 'line',
            markLine: {
              label: {
                show: true,
                position: 'start',
                formatter: '最大值\n'+max
              },
              lineStyle: {
                color: '#007bff'
              },
              data: [
                {
                  yAxis: max,
                  lineStyle: {
                    color: '#77D048',
                    width: 1,
                    opacity: 0.8
                  }
                }
              ]
            }
          }

        ]
      };      
    chartInstance.setOption(options);
  }, [data]);

  return (
    
    <div ref={chartRef} style={{ width: '100%', height: '400px' }}></div>
  );
};

export default QuestionAnswerChart;
