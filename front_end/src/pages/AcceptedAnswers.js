import React, { Component } from 'react'
import axios from "axios";
import ChartPersent from "../componentes/ChartPersent"
import SccaterTimeChart from "../componentes/SccaterTimeChart"
import AcceptedLessVotePercentageChart from '../componentes/AcceptedLessVotePercentageChart';
async function fetchPersent() {
    try {
      const response = await axios.get('http://localhost:8080/java/question/accept-rate');
      const data = response.data;
      console.log(data);
      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return [];
    }
  }

  async function fetchTime() {
    try {
      const response = await axios.get('http://localhost:8080/java/question/accept-interval');
      const data = response.data;
      console.log(data);
      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return [];
    }
  }

  async function fetchAcceptedLessVotePercentage() {
    try {
      const response = await axios.get('http://localhost:8080/java/question/acceptedLessVotePercentage');
      const data = response.data;
      console.log(data);
      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return [];
    }
  }

export default class AcceptedAnswers extends Component {
    constructor(props) {
        super(props);
        this.state = {
          accept_rate:0,
          accept_interval:[],
          acceptedLessVotePercentage:0,
        };
      }
      async componentWillMount() {
        try {
          const fetchedPersent =await fetchPersent();
          const fetchedTime =await fetchTime();
          const fetchedAcceptedLessVotePercentage=await fetchAcceptedLessVotePercentage();
          this.setState({accept_rate:fetchedPersent});
          this.setState({ accept_interval:fetchedTime});
          this.setState({acceptedLessVotePercentage:fetchedAcceptedLessVotePercentage});
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      }
    
    

  render() {
    const { accept_rate,accept_interval,acceptedLessVotePercentage} = this.state;
    return (
      <div>
          <h3>展示有 accepted answer 的问题的百分比:</h3>
          <div>
          <ChartPersent has={accept_rate} nhas={100-accept_rate}/>
          </div>
          <h3>展示问题从提出到解决 (answer accepted time – question post time) 的时间间隔分布(单位:h):</h3>
          <div>
          <SccaterTimeChart data={accept_interval}/>
          </div>
          <h3>展示含有 non-accepted answer 的 upvote 数高于 accepted answer 的问题的百分比:</h3>
          <AcceptedLessVotePercentageChart more={acceptedLessVotePercentage}/>

      </div>
    )
  }
}
