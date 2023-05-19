import React, { Component } from 'react'
import axios from "axios";
import SccaterChart from "../componentes/SccaterChart"
import UserEngage from "../componentes/UserEngage"

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
          this.setState({answer_count_distribution:fetchedAnswerCount});
          this.setState({comment_count_distribution:fetchedCommentCount});
          this.setState({user_engage:fetchedUserEngage});
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      }

    constructor(props) {
        super(props);
        this.state = {
            answer_count_distribution: [],
            comment_count_distribution:[],
            user_engage:Object
        };
    }

  render() {
    const { answer_count_distribution,comment_count_distribution,user_engage} = this.state;
    return (
      <div>
          <h3>展示参与 Thread 讨论的用户数量的分布:</h3>

          <h3>回答者分布 :</h3>
          <SccaterChart data={answer_count_distribution}/>
          
          <h3>评论者分布 :</h3>
          <SccaterChart data={comment_count_distribution}/>

          <h3>展示哪些用户参与 java thread 讨论最活跃 </h3>
          <UserEngage data={user_engage}/>

      </div>
    )
  }
}
