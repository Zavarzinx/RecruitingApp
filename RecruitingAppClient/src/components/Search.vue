<template>
    <div id="show-resumes">
        <form role="form" class="col-md-9 go-right">
            <h2>Search for resume</h2>
            <div class="form-group">
                <input id="search" name="search" type="text" v-model="search" placeholder="search resumes" class="form-control" />
                <label for="search">Title</label>
            </div>
        </form>
        <div  v-for="list in filteredResumes" :key="list.id" class="single-resume">
            <h2> <router-link :to="{ name: 'SingleResume', params: { id: list.id }}">{{ list.title }}</router-link></h2>
            <article>Location: {{ list.region }}</article>
            <article>{{ list.text }}</article>
        </div>
        <b-pagination
                :total-rows="rows"
                v-model="currentPage"
                :per-page="perPage"
        ></b-pagination>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'
    export default {
        data () {
            return {
                perPage: 5,
                currentPage: 1,
                resumes: [],
                search: ''
            }
        },
        methods: {
            getResumes(){
                AXIOS.get('/resume/all',
                    {headers: {
                            'Content-Type': 'application/json',
                            'Authorization':localStorage.token,
                            'Access-Control-Allow-Origin': "*"
                        }})
                    .then(response => {
                        this.resumes = response.data
                        console.log(response.data)
                            .catch(e => {
                                this.errors.push(e)
                            })
                    })

            }

        },
        beforeMount(){
            this.getResumes()
        },
        computed: {
            filteredResumes(){
                return this.resumes.filter((resume) => {
                    return resume.title.toUpperCase().match(this.search.toUpperCase()) || resume.text.toUpperCase().match(this.search.toUpperCase());
                }).slice( (this.currentPage - 1) * this.perPage,
                    this.currentPage * this.perPage);;
            },
            rows() {
                return this.resumes.length
            }
        }
    }
</script>

<style scoped>
    #show-resumes{
        max-width: 800px;
        margin: 0px auto;
    }
    .single-resume{
        padding: 20px;
        margin: 20px 0;
        box-sizing: border-box;
        background: #eee;
    }
    .form-control{
        background: transparent;
    }
    form {
        width: 320px;
        margin: 20px;
    }
    form > div {
        position: relative;
        overflow: hidden;
    }
    form input, form textarea {
        width: 100%;
        border: 2px solid gray;
        background: none;
        position: relative;
        top: 0;
        left: 0;
        z-index: 1;
        padding: 8px 12px;
        outline: 0;
    }
    form input:valid, form textarea:valid {
        background: white;
    }
    form input:focus, form textarea:focus {
        border-color: #357EBD;
    }
    form input:focus + label, form textarea:focus + label {
        background: #357EBD;
        color: white;
        font-size: 70%;
        padding: 1px 6px;
        z-index: 2;
        text-transform: uppercase;
    }
    form label {
        -webkit-transition: background 0.2s, color 0.2s, top 0.2s, bottom 0.2s, right 0.2s, left 0.2s;
        transition: background 0.2s, color 0.2s, top 0.2s, bottom 0.2s, right 0.2s, left 0.2s;
        position: absolute;
        color: #999;
        padding: 7px 6px;
        font-weight: normal;
    }
    form textarea {
        display: block;
        resize: vertical;
    }
    form.go-bottom input, form.go-bottom textarea {
        padding: 12px 12px 12px 12px;
    }
    form.go-bottom label {
        top: 0;
        bottom: 0;
        left: 0;
        width: 100%;
    }
    form.go-bottom input:focus, form.go-bottom textarea:focus {
        padding: 4px 6px 20px 6px;
    }
    form.go-bottom input:focus + label, form.go-bottom textarea:focus + label {
        top: 100%;
        margin-top: -16px;
    }
    form.go-right label {
        border-radius: 0 5px 5px 0;
        height: 100%;
        top: 0;
        right: 100%;
        width: 100%;
        margin-right: -100%;
    }
    form.go-right input:focus + label, form.go-right textarea:focus + label {
        right: 0;
        margin-right: 0;
        width: 40%;
        padding-top: 5px;
    }
</style>