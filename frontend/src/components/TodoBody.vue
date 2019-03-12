<template>
  <div class="container">
    <!-- Email Box -->
    <div class="box">
    <!-- <h3>ADD YOUR MEMO</h3> -->
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group
        label-cols="1"
        label="할일 :"
        label-align="left"
        label-for="submitTodo"
        >
        <b-form-input id="submitTodo"
                      type="text"
                      v-model="form.todoContent"
                      required
                      placeholder="메모 작성...">
        </b-form-input>
      </b-form-group>
      <b-form-group
        label-cols-sm="1"
        label="태그 :"
        label-align-sm="left"
        label-for="tagSet"
        description="태그는 등록된 할일ID만 등록 가능합니다. 등록되지 않은 ID는 태깅이 되지 않습니다."
        >
        <b-form-input id="tagSet"
                      type="text"
                      v-model="form.tagInput"
                      placeholder="태그 구분은 comma(,)를 사용하세요."/>
      </b-form-group>
      <b-button type="submit" variant="dark" size="sm">{{ buttonMode }}</b-button>
      <b-button type="reset" variant="danger" size="sm">CLEAR</b-button>
    </b-form>
    </div>
    <!-- Coupon List Box -->
    <h4>TODO LIST</h4>
    <div class="page-count-box">
    <b-form-select v-model="bFormSelected" @change="changePageCount" :options="countOption" size="10" />
    </div>
    <div>
      <b-table
      selectable
      select-mode="single"
      selectedVariant="secondary"
      :small=true head-variant="dark" hover :items="todos"
      :fields="fields" :show-empty=true
      @row-selected="rowSelected"
      empty-text="There are no records to show."
      empty-filtered-text="There are no records to show."
      >
        <template slot="table-caption empty">
            Blah
        </template>

        <template slot="todoContent" slot-scope="data">
            {{ data.item.todoContent }} @ {{data.item.tagSet}}
        </template>

        <template slot="checkFinish" slot-scope="row">
        <b-button size="sm" @click="modifyTodoDataForFinish(row.item, row.index, $event.target)" class="mr-1">
          CHECK
        </b-button>
        </template>

      </b-table>
      </div>
      <b-pagination align="center" size="sm" :total-rows="totalRow"
               v-model="pageNum" @change="changePageNum" :per-page="pageCount">
      </b-pagination>
    <br>
  </div>
</template>

<script>
import swal from 'sweetalert2'

const pageCountSelect = [
  {value: 5, text: '5'},
  {value: 10, text: '10'},
  {value: 15, text: '15'}
]

export default {
  name: 'TodoBody',
  data () {
    return {
      form: {
        todoContent: '',
        tagInput: ''
      },
      buttonMode: 'ADD',
      tagSet: [],
      apiUrl: 'http://localhost:8080/todo',
      show: true,
      fields: [
        {key:'id', label: 'ID'},
        {key:'todoContent', label: '할일'},
        {key:'createDttm', label: '생성일시'},
        {key:'modifyDttm', label : '변경일시'},
        {key: 'finished', label : '완료여부',
         formatter: value => {
                        return (value) ? 'O' : 'X'
                    }
        },
        {key: 'checkFinish', label : '완료체크'}
      ],
      todos: [],
      selected: [],
      bFormSelected: 5,
      totalRow: 0,
      pageNum: 1,
      pageCount: 5,
      countOption: pageCountSelect
    }
  },
  mounted () {
    this.getTodoListByPaging()
  },
  methods: {
    onSubmit (evt) {
      evt.preventDefault()
      this.makeTagSet()
      this.addTodoData()
    },
    onReset (evt) {
      evt.preventDefault()
      /* Reset our form values */
      this.form.todoContent = ''
      this.form.tagInput = ''
      /* Trick to reset/clear native browser form validation state */
      this.show = false
      this.$nextTick(() => { this.show = true }) /* nextTick : 전체가 렌더링된 상태 보장 */
      this.selected = []
      this.buttonMode = 'ADD'
    },

    rowSelected: function(item) {
        var data, tags=''

        if(item.length == 0) {
            this.selected = []
            return;
        }

        data = item[0]
        this.selected = data
        this.buttonMode = 'EDIT'

        if(data.tagSet.length > 0) {
            data.tagSet.forEach(function(value) {
                tags += value + ','
            })
        }
        // $('#tagSet').val(tags.substring(0, tags.length - 1))
        // $('#submitTodo').val(data.todoContent)
    },

    makeTagSet: function(){
        var that = this,
            tagInput = this.form.tagInput

        if(!tagInput) {
            console.log("tagset empty")
            return;
        }

        tagInput.split(',').forEach(function(value){
            that.tagSet.push(value*1) // string to int
        });
    },

    addTodoData: function(){
      var that = this;

      this.$axios.post(this.apiUrl + '/data',
        {
          todoContent: this.form.todoContent,
          tagSet: this.tagSet
        }
      ).then(function (response) {
        swal({
          type: 'success',
          text: 'TODO LIST에 등록 되었습니다.'
        }).then(() => {
          that.getTodoListByPaging()
        })
        that.form.todoContent = ''
        that.form.tagInput = ''
      })
    },

    modifyTodoDataForFinish: function(item, index, button) {
        var that = this
        console.log(item)
        console.log(index)
        console.log(button)

        this.$axios.patch(this.apiUrl + '/finish',
          {
            id: item.id
          }
        ).then(function (response) {
          swal({
            type: 'success',
            text: '완료처리 되었습니다.'
          }).then(() => {
            that.getTodoListByPaging()
          })
        })
        .catch(function(error) {
            swal({
                type: '',
                text: '미완료된 태그가 존재합니다.'
            })
        })
    },

    getTodoListByPaging: function () {

      this.$axios.get(this.apiUrl+'/list', {
        params: {
          pageNum: this.pageNum,
          pageCount: this.pageCount
        }
      }).then(function (response) {
        this.todos = response.data.list
        this.totalRow = response.data.totalRow
      }.bind(this))
      .catch(function(error) { // out of 2xx
          console.log('out of 2xx!')
      })
    },

    changePageNum: function (page) {
      this.pageNum = page
      this.getTodoListByPaging()
    },

    changePageCount: function (count) {
      this.pageCount = count
      this.getTodoListByPaging()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
div .box {
  margin-top: 60px;
  margin-bottom: 100px;
}
div .page-count-box {
  width: 10% !important;
}

button {
  margin-top: 5px;
  margin-left: 5px;
}

.tags-input{
    display:-webkit-box;
    display:-ms-flexbox;
    display:flex;
    -ms-flex-wrap:wrap;
    flex-wrap:wrap;
    background-color:#fff;
    border-width:1px;
    border-radius:.25rem;
    padding:.5rem 1rem .25rem .5rem;
}

.tags-input-remove {
    color: #2779bd;
    font-size: 1.125rem;
    line-height: 0.7;
}
</style>
