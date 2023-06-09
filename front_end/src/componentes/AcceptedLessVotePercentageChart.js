import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts';


const AcceptedLessVotePercentageChart = ({more}) => {
  const chartRef = useRef(null);

  useEffect(() => {
    // 创建ECharts实例
    const chart = echarts.init(chartRef.current);

    // 定义数据
    const data = [
      { name: ' 高于', value: more },
      { name: '低于', value: 100-more},
    ];

    // 配置选项
    const options = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {d}%',
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
  }, [more]);

  return <div ref={chartRef} style={{ width: '100%', height: '400px' }} />;
};

export default AcceptedLessVotePercentageChart;
