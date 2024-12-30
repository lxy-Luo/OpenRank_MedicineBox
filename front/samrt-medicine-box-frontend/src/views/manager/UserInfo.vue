<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入用户名查询" style="width: 200px" v-model="username"></el-input>
            <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="operation">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
                <el-table-column prop="username" label="用户名" show-overflow-tooltip></el-table-column>
                <el-table-column prop="name" label="真实姓名" show-overflow-tooltip></el-table-column>
                <el-table-column prop="sex" label="性别"></el-table-column>
                <el-table-column prop="phone" label="手机号" show-overflow-tooltip></el-table-column>
                <el-table-column prop="device" label="设备号" show-overflow-tooltip></el-table-column>
                <el-table-column prop="info" label="备注" show-overflow-tooltip></el-table-column>
                <!-- <el-table-column prop="user" label="创建人"></el-table-column> -->

                <el-table-column label="操作" width="180" align="center">
                    <template v-slot="scope">
                        <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
                        <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
                    :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
                </el-pagination>
            </div>
        </div>


        <el-dialog title="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
            <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
                <el-form-item prop="name" label="真实姓名">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="username" label="用户名">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="phone" label="手机号">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="info" label="备注">
                    <el-input v-model="form.info" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="device" label="设备号">
                    <el-input v-model="form.device" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="sex" label="性别">
                    <el-radio v-model="form.sex" label="男">男</el-radio>
                    <el-radio v-model="form.sex" label="女">女</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
import { addUser, deleteBatch, deleteUserById, getAllUser, updateUser } from '@/api/user';

export default {
    name: "UserInfo",
    data() {
        return {
            username:'',
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            name: '',
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                ],
                name: [
                    { required: true, message: '请输入真实姓名', trigger: 'blur' },
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                ],
                sex: [
                    { required: true, message: '选择性别', trigger: 'blur' },
                ],
                device: [
                    { required: true, message: '请输入设备号', trigger: 'blur' },
                ],
            },
            ids: []
        }
    },
    created() {
        this.load(1)
    },
    methods: {
        handleAdd() {   // 新增数据
            this.form = {}  // 新增数据的时候清空数据
            this.fromVisible = true   // 打开弹窗
            this.name = this.tableData.name
            this.sex = this.tableData.sex
        },
        handleEdit(row) {   // 编辑数据
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    if (this.form.id) {
                        updateUser(this.form)
                            .then(res => {
                                if (res.code === '200') {  // 表示成功保存
                                    this.$message.success('保存成功')
                                    this.load(1)
                                    this.fromVisible = false
                                } else {
                                    this.$message.error(res.msg)  // 弹出错误的信息
                                }
                            })
                    } else {
                        addUser(this.form)
                            .then(res => {
                                if (res.code === '200') {  // 表示成功保存
                                    this.$message.success('保存成功')
                                    this.load(1)
                                    this.fromVisible = false
                                } else {
                                    this.$message.error(res.msg)  // 弹出错误的信息
                                }
                            })
                    }
                    // this.$request({
                    //     url: this.form.id ? '/notice/update' : '/notice/add',
                    //     method: this.form.id ? 'PUT' : 'POST',
                    //     data: this.form
                    // }).then(res => {
                    //     if (res.code === '200') {  // 表示成功保存
                    //         this.$message.success('保存成功')
                    //         this.load(1)
                    //         this.fromVisible = false
                    //     } else {
                    //         this.$message.error(res.msg)  // 弹出错误的信息
                    //     }
                    // })
                }
            })
        },
        del(id) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', { type: "warning" }).then(response => {
                deleteUserById(id)
                // this.$request.delete('/notice/delete/' + id)
                .then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => {
            })
        },
        handleSelectionChange(rows) {   // 当前选中的所有的行数据
            this.ids = rows.map(v => v.id)   //  [1,2]
        },
        delBatch() {   // 批量删除
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
            this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
                deleteBatch(this.ids)
                // this.$request.delete('/notice/delete/batch', { data: this.ids })
                .then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => {
            })
        },
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            getAllUser({
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                username: this.username,
            })
                // this.$request.get('/notice/selectPage', {
                //     params: {
                //         pageNum: this.pageNum,
                //         pageSize: this.pageSize,
                //         title: this.title,
                //     }
                // })
                .then(res => {
                    console.log(res)
                    this.tableData = res.data?.list
                    console.log(this.tableData)
                    this.total = res.data?.total
                })
        },
        reset() {
            this.title = null
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
    }
}
</script>

<style scoped></style>