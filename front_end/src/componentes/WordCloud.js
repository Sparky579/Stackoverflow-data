import React from 'react';
import ReactWordcloud from 'react-wordcloud';
import { scaleLinear } from 'd3-scale';

const WordCloud = ({data}) => {
    const maxWeight = Math.max(...data.map(item => item.value));

    // 设置字体大小的比例尺
    const fontSizeScale = scaleLinear()
        .domain([0, maxWeight])
        .range([10, 100]); // 设置合适的字体大小范围

    // 为每个词汇对象设置字体大小
    const wordCloudData = data.map(item => ({
        text: item.text,
        value: item.value,
        fontSize: fontSizeScale(item.value),
    }));
    return (
      <div>
        <ReactWordcloud words={data} />
      </div>
    );
  };
  export default WordCloud;