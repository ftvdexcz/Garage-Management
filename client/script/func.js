'use stricts';

class CustomError extends Error {
  constructor(message, statusCode) {
    super(message);
    this.name = 'CustomError';
    this.statusCode = statusCode;
  }

  logging() {
    console.error(`Error: ${this.message}, Code: ${this.statusCode}`);
  }
}

const verify_token = async (token) => {
  try {
    const result = await call_api(
      'POST',
      VERIFY_TOKEN_API,
      {},
      {
        Authorization: `Bearer ${token}`,
      }
    );

    return result;
  } catch (error) {
    throw error;
  }
};

const call_api = async (method, url, data = {}, headers = {}) => {
  try {
    const r = await fetch(url, {
      method: method,
      headers: {
        'Content-Type': 'application/json',
        ...headers,
      },
      body: data ? JSON.stringify(data) : null,
    });

    if (r.status === 200 || r.status === 201) {
      return r.json();
    } else if (r.status === 204) {
      return;
    }
    const err = await r.json();
    throw new CustomError(err.message, err.statusCode);
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const redirect = (url) => {
  window.location.href = url;
};

const fetchData = async (
  page = 1,
  size = 10,
  dataSource,
  template_callback
) => {
  $('#pagination-container').pagination({
    dataSource: dataSource,
    alias: {
      pageNumber: 'page',
      pageSize: 'size',
    },
    locator: 'data.content',
    totalNumberLocator: function (response) {
      return response.data.totalElements;
    },
    pageSize: size,
    ajax: {
      beforeSend: function (xhr) {
        xhr.setRequestHeader('Authorization', `Bearer ${token}`);
        // $('#loading').html(`<h4 style="text-align:center">...</h4>`);
      },
      complete: function (jqXHR, textStatus) {
        if (
          jqXHR.status === 200 ||
          jqXHR.readyState == 0 ||
          jqXHR.status == 0
        ) {
          return false; // do nothing
        } else if (
          (jqXHR && jqXHR.status === 403) ||
          (jqXHR && jqXHR.status === 401)
        ) {
          redirect(ERROR_403_HTML);
        } else {
          redirect(ERROR_500_HTML);
        }
      },
    },
    callback: function (data, pagination) {
      const html = template_callback(data);
      $('#data-container').html(html);
      // $('#loading').html('');
    },
    className: 'paginationjs-theme-blue paginationjs-big',
  });
};

const checkRole = async function (role) {
  if (!token) redirect(ERROR_403_HTML);

  try {
    const result = await verify_token(token);

    return result.data;
  } catch (error) {
    error.logging();

    redirect(ERROR_403_HTML);
  }
};
