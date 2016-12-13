import React from 'react'
import echarts from 'echarts'
import $ from 'jquery';          //jquery

export default class AreaStack extends React.Component{

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
  autoResize() {
    var areastack = document.getElementById('areastack-chart');
    areastack.style.width = (5*window.innerWidth/6 - 240)+'px';
    areastack.style.height = (window.innerHeight - 90)+'px';
  }


  componentDidMount() {
    this.autoResize();
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('areastack-chart'));
    // 绘制图表
    myChart.setOption({
        title: {
            text: '历年新闻记录数'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['记录数']
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
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
    window.onresize = function () {
        this.autoResize();
        myChart.resize();
    }.bind(this);
  }

  render() {
    return (
			<div id = "areastack-chart">
			</div>
    );
  }
}
