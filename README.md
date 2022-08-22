# Visual Paradigm plugin Export ER Description for PostgreSQL
## What is it
Visual Paradigm plugin, which exports description of Tables and Columns
as SQL script with comments. 

Example of result:

```sql
COMMENT ON TABLE cd IS 'Table CD';
COMMENT ON COLUMN cd.c IS 'Column c';
COMMENT ON COLUMN cd.d IS 'DSA''s';
COMMENT ON TABLE ab IS 'Table AB';
COMMENT ON COLUMN ab.a IS 'Column А';
COMMENT ON COLUMN ab.b IS 'Column B';
```

## How to use
1. Download a release with the plugin
2. Install then plugin into Visual Paradigm (Help - Install plugin)
3. Click on Export comments as DDL in context menu of ER Diagram