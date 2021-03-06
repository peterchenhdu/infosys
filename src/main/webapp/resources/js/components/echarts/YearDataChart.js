import './style/charts.css'
import React from 'react'
import echarts from 'echarts'
import $ from 'jquery';          //jquery

export default class YearDataChart extends React.Component{

  constructor(props){
    super(props);
    this.chartData={
      years:[],
      yearCounts:[]
    };
    $.ajax({
     async: false,
      type : "get",
      url : "/infosys/news/yearcount",
      data: {},
      datatype : 'json',
      success : function(data) {
        for(var year in data){
          this.chartData.years.push(year+'年');
          this.chartData.yearCounts.push(data[year]);
        }
      }.bind(this),
	  error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
	  }
    });

  }



  componentDidMount() {

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('areastack-chart'));
    // 绘制图表
    myChart.setOption({
        title: {
            left: 'center',
            text: '历年新闻记录数'
        },
        tooltip : {
            trigger: 'axis'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                name :'年份',
                data : this.chartData.years
            }
        ],
        yAxis : [
            {
                type : 'value',
                name:'总记录数'
            }
        ],
        series : [
            {
                name:'记录数',
                type:'line',
                stack: '记录数',
                smooth:true,
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                itemStyle : {
                    normal : {
                        color:'#29C8E8',
                        borderWidth:1,
                        borderColor:'#29C8E8',
                        areaStyle: {type: 'default',color:'#DAEFF4'},
                      lineStyle:{
                        color:'#29C8E8'
                      }
                    }
                  },
                data:this.chartData.yearCounts
            },

        ]
    });

  }

  render() {
    return (
			<div id = "areastack-chart">
			</div>
    );
  }
}
