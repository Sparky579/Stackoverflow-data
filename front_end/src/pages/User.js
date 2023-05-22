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


export default class User extends Component {


    async componentWillMount() {
        try {
          const fetchedAnswerCount =await fetchCount("http://localhost:8080/question/answer/count");
          const fetchedCommentCount =await fetchCount("http://localhost:8080/question/comment/count");
          const fetchedUserEngage =await fetchCount("http://localhost:8080/question/user/engage");
          const fetchedMergeCount = await fetchCount("http://localhost:8080/question/merge-count");
          this.setState({answer_count_distribution:fetchedAnswerCount});
          this.setState({comment_count_distribution:fetchedCommentCount});
          this.setState({user_engage:fetchedUserEngage});
          this.setState({merge_count_distribution:fetchedMergeCount})
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      }

    constructor(props) {
        super(props);
        this.state = {
          merge_count_distribution:[],
            answer_count_distribution: [],
            comment_count_distribution:[],
            user_engage:Object
        };
    }

  render() {
    const { answer_count_distribution,comment_count_distribution,user_engage,merge_count_distribution} = this.state;
    return (
      <div>
          <h3>展示参与 Thread 讨论的用户数量的分布:</h3>
          <h4>总体分布 :</h4>
          <h5>散点图(不同问题的参与者者数量):</h5>
          <SccaterChart data={merge_count_distribution} xname={"问题序号"} yname={"讨论数量"}/>
          <h5>直方图(不同参与者数量的频数):</h5>
          <QuestionAnswerCount data={merge_count_distribution} xname={"参与人数"} yname={"问题个数"}/>
          <h4>回答者分布 :</h4>
          <h5>散点图(不同问题的回答者数量):</h5>
          <SccaterChart data={answer_count_distribution} xname={"问题序号"} yname={"回答数量"}/>
          <h5>直方图(不同回答者数量的频数):</h5>
          <QuestionAnswerCount data={answer_count_distribution} xname={"回答人数"} yname={"问题个数"}/>
          
          <h4>评论者分布 :</h4>
          <h5>散点图(不同问题的评论者数量):</h5>
          <SccaterChart data={comment_count_distribution}  xname={"问题序号"} yname={"评论数量"}/>
          <h5>直方图(不同评论者数量的频数):</h5>
          <QuestionAnswerCount data={comment_count_distribution} xname={"评论人数"} yname={"问题个数"}/>
          <h3>展示哪些用户参与 java thread 讨论最活跃 </h3>
          <UserEngage data={user_engage} xname={"用户id"} yname={"活跃次数"}/>
          <LineChart data={user_engage} xname={"用户id"} yname={"活跃次数"}/>

      </div>
    )
  }
}
