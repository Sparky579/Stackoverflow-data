import React, { Component } from 'react'
import axios from "axios";
import SccaterChart from "../componentes/SccaterChart"
import UserEngage from "../componentes/UserEngage"
import QuestionAnswerCount from "../componentes/QuestionAnswerCount"
import LineChart from "../componentes/LineChart"

async function fetchCount(url) {
    try {
        const response = await axios.get(url);
        const data = response.data;
        console.log(data);
        
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}


export default class Advance extends Component {


    async componentWillMount() {
        try {
          const fetchedPopApi =await fetchCount("http://localhost:8080/question/api-distribution");
          this.setState({pop_api:fetchedPopApi});

        } catch (error) {
          console.error('Error fetching data:', error);
        }
      }

    constructor(props) {
        super(props);
        this.state = {
            pop_api:Object
        };
    }

  render() {
    const { pop_api} = this.state;
    return (
      <div>

          <h3>展示哪些 Java APIs 在 Stack Overflow 上最频繁被讨论</h3>
          <UserEngage data={pop_api}  xname={"Api"} yname={"讨论次数"}/>
          <LineChart data={pop_api} xname={"Api"} yname={"讨论次数"}/>

      </div>
    )
  }
}
