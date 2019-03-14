<template>
  <div class="container">
    <div class="box">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show">
        <b-form-group
          label-cols="1"
          label="할일 :"
          label-align="left"
          label-for="submitTodo">
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
          description="태그는 등록된 할일ID만 등록 가능합니다. 등록되지 않은 ID는 태깅이 되지 않습니다.">
          <b-form-input id="tagSet"
                        type="text"
                        v-model="form.tagInput"
                        placeholder="태그 구분은 comma(,)를 사용하세요."/>
        </b-form-group>
        <b-button type="submit" variant="dark" size="sm">ADD</b-button>
        <b-button type="reset" variant="danger" size="sm">CLEAR</b-button>
      </b-form>
    </div>

    <h4>TODO LIST</h4>
    <div class="page-count-box">
      <b-form-select v-model="bFormSelected" @change="changePageCount" :options="countOption" size="10" />
    </div>
    <div>
      <b-table
        selectable
        select-mode="single"
        selectedVariant="secondary"
        :small=true head-variant="dark" hover
        :items="todos"
        :fields="fields"
        :tbody-tr-class="rowClass"
        :show-empty=true
        @row-selected="rowSelected"
        empty-text="There are no records to show."
        empty-filtered-text="There are no records to show.">
        <template slot="todoContent" slot-scope="data">
            {{ data.item.todoContent }} @ {{data.item.tagSet}}
        </template>
        <template slot="checkFinish" slot-scope="row">
          <b-button size="sm" variant="outline-dark" @click="checkFinishTodo(row.item)" class="mr-2">
            ✔️
          </b-button>
        </template>
      </b-table>

      <!-- Info modal -->
      <b-modal
        id="modalInfo"
        ref="modal"
        title="Edit your MEMO"
        @ok="handleOk">
          <form @submit.stop.prevent="modifyTodoData">
            <b-row clas="mb-1">
              <b-col cols="2">할일</b-col>
              <b-col><b-form-input type="text" placeholder="Edit your memo" v-model="modalInfo.currMemo" /></b-col>
            </b-row>
            <b-row clas="mb-1">
              <b-col cols="2">태그</b-col>
              <b-col><b-form-input type="text" placeholder="Edit your tag set" v-model="modalInfo.currTags" /></b-col>
            </b-row>



          </form>
      </b-modal>
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
      tagSet: [],
      apiUrl: 'http://localhost:9000/todo/',
      show: true,
      fields: [
        {key:'id', label: 'ID'},
        {key:'todoContent', label: '할일'},
        {key:'createDttm', label: '생성일시',
          formatter: value => { return value.substring(2, 16)}
        },
        {key:'modifyDttm', label : '변경일시',
          formatter: value => { return value.substring(2, 16)}
        },
        {key: 'finished', label : '완료여부',
         formatter: value => {
                        return (value) ? 'O' : 'X'
                    }
        },
        {key: 'checkFinish', label : '완료체크'}
      ],
      todos: [],
      bFormSelected: 5,
      totalRow: 0,
      pageNum: 1,
      pageCount: 5,
      countOption: pageCountSelect,
      modalInfo: {currMemo: '', currTags: '', id: '', createDttm: '', finished: false}
    }
  },

  mounted () {
    this.getTodoListByPaging()
  },

  methods: {

    handleOk(evt) {
        // Prevent modal from closing
      evt.preventDefault()
      if (!this.modalInfo.currMemo) {
        alert('Please enter your memo')
      } else {
        this.modifyTodoData()
      }
    },

    rowClass(item, type) {
      if(!item) return
      if(item.finished === true) return 'table-success'
    },

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
    },

    rowSelected: function(item) {
        var data

        if(item.length == 0) {
            return;
        }

        data = item[0]

        this.modalInfo = {
          currMemo: data.todoContent,
          currTags: data.tagSet,
          id: data.id,
          createDttm: data.createDttm,
          finished: false
        }

        this.$root.$emit('bv::show::modal', 'modalInfo', null)
    },

    makeTagSet: function() {
      var that = this,
        tagInput = this.form.tagInput

      if(!tagInput) {
          return;
      }

      tagInput.split(',').forEach(function(value){
          that.tagSet.push(value*1) // string to int
      });
    },

    addTodoData: function() {
      var that = this;

      this.$axios.post(this.apiUrl,
        {
          todoContent: this.form.todoContent,
          tagSet: this.tagSet
        }
      ).then(function (response) {
        swal({
          type: 'success',
          title: 'TODO LIST에 등록되었습니다.',
          showConfirmButton: false,
          timer: 1000
        }).then(() => {
          that.getTodoListByPaging()
        })
        that.form.todoContent = ''
        that.form.tagInput = ''
      })
    },

    modifyTodoData() {
      var that= this,
          tagSetParam = []

      if(this.modalInfo.currTags.length > 0) {
        this.modalInfo.currTags.split(',').forEach(function(value){
          if(value) {
            tagSetParam.push(value*1) // string to int
          }
        });
      }

        // modify!
      this.$axios.patch(this.apiUrl,
        {
          id: this.modalInfo.id,
          tagSet: tagSetParam,
          todoContent: this.modalInfo.currMemo,
          createDttm: this.modalInfo.createDttm,
          finished: this.modalInfo.finished
        }
      ).then(function (response) {
        swal({
          type: 'success',
          title: '수정이 완료되었습니다.',
          showConfirmButton: false,
          timer: 1500
        }).then(() => {
          that.getTodoListByPaging()
        })
      })
      .catch(function(error) {
        swal({
          type: '',
          text: '수정에 실패했습니다.'
        })
      })

      this.$nextTick(() => {
        // Wrapped in $nextTick to ensure DOM is rendered before closing
        this.$refs.modal.hide()
        this.getTodoListByPaging()
      })
    },

    checkFinishTodo: function(item) {
      var that = this

      this.$axios.patch(this.apiUrl + 'toggle',
        {
          id: item.id
        }
      ).then(function (response) {
        var msg

        if(response.data.responseMessage === 'check') {
          msg = '완료처리 되었습니다.'
        } else {
          msg = '미완료처리 되었습니다.'
        }
        swal({
          type: 'success',
          title: msg,
          showConfirmButton: false,
          timer: 1500
        }).then(() => {
          that.getTodoListByPaging()
        })
      })
      .catch(function(error) {
        swal({
          type: 'error',
          title: error.response.data.responseMessage,
          showConfirmButton: false,
          timer: 2000
        })
      })
    },

    getTodoListByPaging: function () {
      this.$axios.get(this.apiUrl, {
        params: {
          pageNum: this.pageNum,
          pageCount: this.pageCount
        }
      }).then(function (response) {
        this.todos = response.data.list
        this.totalRow = response.data.totalRow
      }.bind(this))
      .catch(function(error) { // out of 2xx
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
</style>
