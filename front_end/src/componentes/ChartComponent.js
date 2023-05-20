import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';


const ChartComponent = ({has,nhas}) => {
  const chartRef = useRef(null);

  useEffect(() => {
    // 创建ECharts实例
    const chart = echarts.init(chartRef.current);

    // 定义数据
    const data = [
      { name: '有答案', value: has },
      { name: '没有答案', value:  nhas},
    ];

    // 配置选项
    const options = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)',
      },
      series: [
        {
          name: '问题百分比',
          type: 'pie',
          radius: ['50%', '70%'],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: 'center',
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '30',
              fontWeight: 'bold',
            },
          },
          labelLine: {
            show: false,
          },
          data: data,
        },
      ],
    };

    // 使用配置项绘制图表
    chart.setOption(options);

    // 在组件卸载时销毁图表实例
    return () => {
      chart.dispose();
    };
  }, [has]);

  return <div ref={chartRef} style={{ width: '100%', height: '400px' }} />;
};

export default ChartComponent;
