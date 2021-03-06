import './style/index.css'
import React from 'react'
import { Row, Col } from 'antd';
import YearDataChart from '../echarts/YearDataChart'
import MonthSearch from '../search/MonthSearch'
import $ from 'jquery';          //jquery
import { Menu, Icon } from 'antd';
const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

export default class Index extends React.Component{

  constructor(props){
    super(props);
	this.state = {currentMenu:'chart'}
	$.ajax({
     async: false,
      type : "get",
      url : "/infosys/index/data",
      data: {},
      datatype : 'json',
      success : function(data) {

          this.state.totalCount=data.totalCount;

      }.bind(this),
	  error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
	  }
    });



  }

  handleMenuClick(e) {
    console.log('click ', e);
    this.setState({
      currentMenu: e.key,
    });
  }

  render() {


    var body = <YearDataChart />;
    switch (this.state.currentMenu) {
      case "chart":
        body = <YearDataChart />;
        break;
      case "search":
        body = <MonthSearch />;
        break;
      default:
        body = <h1>待开发</h1>;
    }

    return (
			<div className="main-div">
        <Row>
          <Col span={20} offset={2}>
            <div className="main-top"><span className='record-num-span'>收集的新闻总记录数：{this.state.totalCount}</span>一个牛逼的系统</div>
          </Col>
        </Row>
        <Row>
          <Menu onClick={this.handleMenuClick.bind(this)} selectedKeys={[this.state.currentMenu]} mode="horizontal">
             <Menu.Item key="home">
               <Icon type="home" />主页
             </Menu.Item>
             <Menu.Item key="realtime-news">
               <Icon type="rocket" />实时新闻
             </Menu.Item>
             <Menu.Item key="search">
               <Icon type="search" />新闻搜索
             </Menu.Item>
             <Menu.Item key="chart">
               <Icon type="line-chart" />图表统计
             </Menu.Item>
             <Menu.Item key="analyze">
               <Icon type="solution" />新闻分析
             </Menu.Item>
             <Menu.Item key="rank">
               <Icon type="bars" />分类排名
             </Menu.Item>
             <Menu.Item key="setting">
               <Icon type="setting" />系统设置
             </Menu.Item>
           </Menu>
        </Row>
        <Row>
          <Col span={20} offset={2}>
            <div className="content-div">
              <div className="news-stat">
                {body}
              </div>
            </div>
          </Col>
        </Row>
			</div>
    );
  }
}
