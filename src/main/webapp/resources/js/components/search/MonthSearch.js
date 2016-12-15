import './style/search.css'
import React from 'react'
import echarts from 'echarts'
import { Table, DatePicker } from 'antd';
import $ from 'jquery';          //jquery
import moment from 'moment';

moment.locale('zh-cn');
const { MonthPicker, RangePicker } = DatePicker;

export default class MonthSearch extends React.Component{

  constructor(props){
    super(props);
    this.state={

    };


    this.columns = [{
      title: '标题',
      dataIndex: 'title',
      render: (text, record) => <a href={record.url} target="_blank">{text}</a>,
    }, {
      title: '第一来源',
      width:150,
      dataIndex: 'crawlerName',
      render: (text, record) => <a href={record.url} target="_blank">{text}</a>,
    }, {
      title: '第二来源',
      width:200,
      dataIndex: 'srcName',
      render: (text, record) => <a href={record.srcUrl} target="_blank">{text}</a>,
    }, {
      title: '发布时间',
      width:180,
      dataIndex: 'publishTime',
    }];




    this.data = [];
    this.param = {};

    var now = new Date();
    var month = now.getFullYear()+"-"+(now.getMonth()+1);
    this.param.month = month;
    this.param.offset = 0;
    this.param.limit = 10;

    this.searchByMonth();

    this.pagination = {
      total: this.totalNum,
      showSizeChanger: true,
      onShowSizeChange: (current, pageSize) => {
        console.log('Current: ', current, '; PageSize: ', pageSize);
        this.param.offset = (current-1)*pageSize;
        this.param.limit = pageSize;
        this.searchByMonth();
      },
      onChange: (current) => {
        console.log('Current: ', current);
        this.param.offset = (current-1)*this.param.limit;
        this.searchByMonth();
      },
    };
  }

  searchByMonth(){
    this.data.splice(0, this.data.length);
    $.ajax({
     async: false,
      type : "get",
      url : "/infosys/news/monthdata",
      data: {month:this.param.month,offset:this.param.offset,limit:this.param.limit},
      datatype : 'json',
      success : function(data) {
          this.searchRst = data.rstData;
          for (let i = 0; i < data.rstData.length; i++) {
            this.data.push({
              key: i,
              title: `${data.rstData[i].title}`,
              crawlerName: `${data.rstData[i].crawlerName}`,
              srcName: `${data.rstData[i].srcName}`,
              publishTime:`${data.rstData[i].publishTime}`,
              url:`${data.rstData[i].url}`,
              srcUrl:`${data.rstData[i].srcUrl}`,
            });
          };
          this.totalNum = data.totalNum;

      }.bind(this),
    error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
    }
    });
  }

  componentDidMount() {


  }

  onTimeChange(date, dateString) {
    console.log(date, dateString);
    this.param.month = dateString;
    this.searchByMonth();
    this.pagination.total = this.totalNum;
    this.setState({
      refresh: true,
    });
  }

  render() {
    return (
			<div >
        日期选择：<MonthPicker onChange={this.onTimeChange.bind(this)} defaultValue={this.param.month}/>
        <br />
        <br />
        <Table columns={this.columns} dataSource={this.data} pagination={this.pagination} size="small"/>
			</div>
    );
  }
}
