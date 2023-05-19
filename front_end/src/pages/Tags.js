import React, { Component } from 'react'
import ReactWordcloud from 'react-wordcloud';
import axios from "axios";
import CombineTagVote from "../componentes/CombineTagVote"
import WordCloud from "../componentes/WordCloud"
import WordCloudChart from "../componentes/WordCloudChart"


async function fetchCount() {
    try {
        const response = await axios.get('http://localhost:8080/java/question/tags/frequent_with_java');
        const data = response.data;
        console.log(data);
        
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}
async function fetchCombineVote() {
    try {
        const response = await axios.get('http://localhost:8080/java/question/tags/combine/mostvote');
        const data = response.data;
        console.log(data);
        
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}

async function fetchCombineView() {
    try {
        const response = await axios.get('http://localhost:8080/java/question/tags/mostview');
        const data = response.data;
        console.log(data);
        
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}


async function fetchVote() {
    try {
        const response = await axios.get('http://localhost:8080/java/question/tags/mostvote');
        const data = response.data;
        console.log(data);
        
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}


export default class Tags extends Component {
    async componentWillMount() {
        try {
            const fetchedCount = await fetchCount();
            const fetchedCombinevote = await fetchCombineVote();
            const fetchedVote=await fetchVote();
            const fetchedCombineview =await fetchCombineView();
            this.setState({ count: fetchedCount });
            this.setState({combinevote: fetchedCombinevote});
            this.setState({vote: fetchedVote});
            this.setState({combineview:fetchedCombineview});
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }


    constructor(props) {
        super(props);
        this.state = {
            count: Object,
            combinevote:Object,
            vote:Object,
            combineview:Object
        };
    }

    render() {
        const { count,combinevote,vote ,combineview} = this.state;
        const wordCloudData = Object.entries(count).map(([text, value]) => ({
            text,
            value,
        }));
        const wordCloudChartData = Object.entries(count).map(([name, value]) => ({
            name,
            value,
        }));



        return (
            <div>
                <h3>展示哪些 tags 经常和 Java tag 一起出现:</h3>
                <WordCloud data={wordCloudData}/>
                <WordCloudChart data={wordCloudChartData}/>
                <h3>展示哪些 tags 或 tag 的组合得到最多的 upvotes:</h3>
                <h4>tag 的组合:</h4>
                <CombineTagVote data={combinevote}/>
                <h4>tags:</h4>
                <CombineTagVote data={vote}/>
                <h3>展示哪些 tags 或 tag 的组合得到最多的 views :</h3>

                <CombineTagVote data={combineview}/>





            </div>
        )
    }
}
