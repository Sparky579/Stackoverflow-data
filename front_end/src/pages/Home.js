import React, { Component } from 'react'
import axios from "axios";
import { Link } from 'react-router-dom';
import ChartComponent from '../componentes/ChartComponent';
import QustionAnswerChart from '../componentes/QuestionAnswerChart'
import AnswerDistributionChart from "../componentes/AnswerDistributionChart"

async function fetchDistribution() {
  try {
    const response = await axios.get('http://localhost:8080/java/question/answer_count/distribution');
    const data = response.data;
    console.log(data);
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}

async function fetchData() {
  try {
    const response = await axios.get('http://localhost:8080/java/question/answer_count/all');
    const data = response.data;
    console.log(data);
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}
async function fetchAverage() {
  try {
    const response = await axios.get('http://localhost:8080/java/question/answer_count/average');
    const data = response.data;
    console.log(data);
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return 0;
  }
}

async function fetchMax() {
  try {
    const response = await axios.get('http://localhost:8080/java/question/answer_count/max');
    const data = response.data;
    console.log(data);
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return 0;
  }
}

async function fetchZeroCount() {
  try {
    const response = await axios.get('http://localhost:8080/java/question/answer_count/zero');
    const data = response.data;
    console.log(data);
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return 0;
  }
}

export default class Home extends Component {

  constructor(props) {
    super(props);
    this.state = {
      average:0,
      data: [],
      max:0,
      zero:0,
      distribution:Object,
    };
  }



  async componentWillMount() {
    try {
      const fetchedZero =await fetchZeroCount();
      const fetchedAverage = await fetchAverage();
      const fetchedData = await fetchData();
      const fetchedMax = await fetchMax();
      const fetchedDistribution = await fetchDistribution();
      console.log("xx",fetchedDistribution);

      this.setState({max: fetchedMax, average: fetchedAverage ,data: fetchedData ,zero: fetchedZero,distribution: fetchedDistribution });
      
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }


  




  render() {
    const { data ,average,max,zero,distribution} = this.state;

    const contentstyle = {
      margin: "auto",
      width: "80%"
    }
    const noticestyle = {
      borderBottom: "2px solid #D3D3D3"
    }

    return (
    <section>
      <h3>展示没有答案的 questions 的百分比:</h3>
      <ChartComponent has={data.length-zero} nhas={zero}/>
      <h3>展示 answer 分布、平均值以及最大值:</h3>
     <QustionAnswerChart data={data} average={average} max={max}/>
     <h3>展示 answer_count 统计值:</h3>
     <AnswerDistributionChart data={distribution}/>
    </section>

    )
  }
}
